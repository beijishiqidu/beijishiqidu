package personal.blog.controller.backend;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.cache.TypeCount;
import personal.blog.form.FormAlert;
import personal.blog.form.PhotoForm;
import personal.blog.service.PhotoService;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Photo;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "admin/photo")
public class PhotoMgtController {

    private static final Logger LOGGER = Logger.getLogger(PhotoMgtController.class);

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView forwardAddPage(Integer firstResult, Integer maxResults) {
        ModelAndView mav = new ModelAndView("admin/photo_add");
        mav.addObject("photoTypeList", photoService.getPhotoTypeList());
        return mav;
    }

    @RequestMapping(value = "/manage/list", method = RequestMethod.GET)
    public ModelAndView forwardPhotoListPage() {
        ModelAndView mav = new ModelAndView("admin/photo_list");
        List<TypeCount> psu = photoService.getPhotoTypeCount();
        mav.addObject("typeList", psu);
        return mav;
    }

    @RequestMapping(value = "manage/detail", method = RequestMethod.GET)
    public ModelAndView forwardPhotoImageListPage() {
        ModelAndView mav = new ModelAndView("admin/photo_detail");
        PageSplitUtil<Photo> psu = photoService.getPhotoListForPage(0, 999, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView savePhotoInfo(String photoId, String title, String type, HttpServletRequest request) {

        PhotoForm photoForm = new PhotoForm();
        photoForm.setTitle(title);
        photoForm.setType(type);

        List<FormAlert> resultList = photoService.validatePhotoForm(photoForm);

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (!resultList.isEmpty()) {
            returnMap.put("result", false);
            returnMap.put("msg", resultList);
        } else {
            try {
                Long id = photoService.savePhotoInfo(photoId, title, type);
                returnMap.put("result", true);
                returnMap.put("msg", "保存成功");
                returnMap.put("photoId", id);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                returnMap.put("result", false);
                returnMap.put("dbSave", true);
                returnMap.put("msg", "保存失败");
            }
        }
        Gson gson = new Gson();
        String result = gson.toJson(returnMap);

        request.setAttribute("callback", "App.submitPhotoInfoCallback");
        request.setAttribute("returnValue", result);

        ModelAndView mav = new ModelAndView("upload");

        return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deletePhoto(Long photoId, Integer firstResult, Integer maxResults) {
        photoService.deletePhotoById(photoId);
        ModelAndView mav = new ModelAndView("admin/ajax_photo_list");
        PageSplitUtil<Photo> psu = photoService.getPhotoListForPage(firstResult, maxResults, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView forwardEditPhotoPage(Long photoId) {
        Photo photo = photoService.getPhotoById(photoId);
        ModelAndView mav = new ModelAndView("admin/photo_add");
        mav.addObject("photoObj", photo);
        mav.addObject("photoTypeList", photoService.getPhotoTypeList());
        return mav;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ModelAndView ajaxRefreshNewsListPage(Integer firstResult, Integer maxResults) {
        ModelAndView mav = new ModelAndView("admin/ajax_photo_list");
        PageSplitUtil<Photo> psu = photoService.getPhotoListForPage(firstResult, maxResults, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/upload/chunk/item", method = RequestMethod.POST)
    public void uploadPhotoChunk(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("==========/upload/chunk/item");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("utf-8");

        String savePath = "D:\\dev\\apache-tomcat-7.0.67\\upload\\image\\webuploader\\photo\\";// request.getContextPath();//
                                                                                               // TODO
        String fileMd5 = null;
        String chunk = null;

        try {
            List<FileItem> items = sfu.parseRequest(request);

            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("fileMd5")) {
                        fileMd5 = item.getString("utf-8");
                    }
                    if (fieldName.equals("chunk")) {
                        chunk = item.getString("utf-8");
                    }
                } else {
                    File file = new File(savePath + "/" + fileMd5);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File chunkFile = new File(savePath + "/" + fileMd5 + "/" + chunk);
                    FileUtils.copyInputStreamToFile(item.getInputStream(), chunkFile);

                }
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (FileUploadException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }


    @RequestMapping(value = "/upload/chunk/merge", method = RequestMethod.POST)
    public void uploadPhotoMergeChunk(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LOGGER.info("==========/upload/chunk/merge");

        String savePath = "D:\\dev\\apache-tomcat-7.0.67\\upload\\image\\webuploader\\photo\\";// request.getContextPath();//
                                                                                               // TODO
        // 合并文件
        // 需要合并的文件的目录标记
        String fileMd5 = request.getParameter("fileMd5");

        // 读取目录里的所有文件
        File f = new File(savePath + "/" + fileMd5);
        File[] fileArray = f.listFiles(new FileFilter() {
            // 排除目录只要文件
            @Override
            public boolean accept(File pathname) {
                // TODO Auto-generated method stub
                if (pathname.isDirectory()) {
                    return false;
                }
                return true;
            }
        });

        // 转成集合，便于排序
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                // TODO Auto-generated method stub
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }
                return 1;
            }
        });
        // UUID.randomUUID().toString()-->随机名
        File outputFile = new File(savePath + "/" + fileMd5 + ".png");
        // 创建文件
        outputFile.createNewFile();
        // 输出流
        FileChannel outChnnel = new FileOutputStream(outputFile).getChannel();
        // 合并
        FileChannel inChannel;
        for (File file : fileList) {
            inChannel = new FileInputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChnnel);
            inChannel.close();
            // 删除分片
            file.delete();
        }
        outChnnel.close();
        // 清除文件夹
        File tempFile = new File(savePath + "/" + fileMd5);
        if (tempFile.isDirectory() && tempFile.exists()) {
            tempFile.delete();
        }
        LOGGER.info("合并成功");

    }

    @RequestMapping(value = "/upload/chunk/check", method = RequestMethod.POST)
    public void uploadPhotoCheckChunk(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LOGGER.info("==========/upload/chunk/check");

        String savePath = "D:\\dev\\apache-tomcat-7.0.67\\upload\\image\\webuploader\\photo";// request.getContextPath();//
                                                                                             // TODO
        savePath = savePath + "\\";

        // 检查当前分块是否上传成功
        String fileMd5 = request.getParameter("fileMd5");
        String chunk = request.getParameter("chunk");
        String chunkSize = request.getParameter("chunkSize");

        File checkFile = new File(savePath + "/" + fileMd5 + "/" + chunk);

        response.setContentType("text/html;charset=utf-8");
        // 检查文件是否存在，且大小是否一致
        if (checkFile.exists() && checkFile.length() == Integer.parseInt(chunkSize)) {
            // 上传过
            response.getWriter().write("{\"ifExist\":1}");
        } else {
            // 没有上传过
            response.getWriter().write("{\"ifExist\":0}");
        }
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/upload/chunk/submit", method = RequestMethod.POST)
    @ResponseBody
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LOGGER.info("==========/upload/chunk/submit");

        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                // 得到所有的表单域，它们目前都被当作FileItem
                List<FileItem> fileItems = upload.parseRequest(request);
                String id = "";
                String fileName = "";
                // 如果大于1说明是分片处理
                int chunks = 1;
                int chunk = 0;
                FileItem tempFileItem = null;
                for (FileItem fileItem : fileItems) {
                    if (fileItem.getFieldName().equals("id")) {
                        id = fileItem.getString();
                    } else if (fileItem.getFieldName().equals("name")) {
                        fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    } else if (fileItem.getFieldName().equals("chunks")) {
                        chunks = NumberUtils.toInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("chunk")) {
                        chunk = NumberUtils.toInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("multiFile")) {
                        tempFileItem = fileItem;
                    }
                }

                // session中的参数设置获取是我自己的原因,文件名你们可以直接使用fileName,这个是原来的文件名
                String fileSysName = String.valueOf(System.currentTimeMillis());
                LOGGER.info("=====================>" + fileName);

                String realname = fileSysName + fileName.substring(fileName.lastIndexOf("."));// 转化后的文件名
                String filePath = "D:\\dev\\apache-tomcat-7.0.67\\upload\\image\\webuploader\\photo\\";// 文件上传路径
                // 临时目录用来存放所有分片文件
                String tempFileDir = filePath + id;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
                File tempPartFile = new File(parentFileDir, realname + "_" + chunk + ".part");
                FileUtils.copyInputStreamToFile(tempFileItem.getInputStream(), tempPartFile);
                // 是否全部上传完成
                // 所有分片都存在才说明整个文件上传完成
                boolean uploadDone = true;
                for (int i = 0; i < chunks; i++) {
                    File partFile = new File(parentFileDir, realname + "_" + i + ".part");
                    if (!partFile.exists()) {
                        uploadDone = false;
                    }
                }
                // 所有分片文件都上传完成
                // 将所有分片文件合并到一个文件中
                if (uploadDone) {
                    // 得到 destTempFile 就是最终的文件
                    File destTempFile = new File(filePath, realname);
                    for (int i = 0; i < chunks; i++) {
                        File partFile = new File(parentFileDir, realname + "_" + i + ".part");
                        FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                        // 遍历"所有分片文件"到"最终文件"中
                        FileUtils.copyFile(partFile, destTempfos);
                        destTempfos.close();
                    }
                    // 删除临时目录中的分片文件
                    FileUtils.deleteDirectory(parentFileDir);
                    LOGGER.error("===============>done");
                } else {
                    // 临时文件创建失败
                    if (chunk == chunks - 1) {
                        FileUtils.deleteDirectory(parentFileDir);
                        LOGGER.error("===============>delete failed");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
