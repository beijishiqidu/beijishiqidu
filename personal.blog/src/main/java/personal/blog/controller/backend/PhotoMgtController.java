package personal.blog.controller.backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
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
import personal.blog.constant.ApplicationConstant;
import personal.blog.form.FormAlert;
import personal.blog.form.PhotoForm;
import personal.blog.service.PhotoService;
import personal.blog.util.EnvUtil;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Photo;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "admin/photo")
public class PhotoMgtController {

    private static final Logger LOGGER = Logger.getLogger(PhotoMgtController.class);

    @Autowired
    private PhotoService photoService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView forwardAddPage(HttpServletRequest request, Integer firstResult, Integer maxResults) {
        ModelAndView mav = new ModelAndView("admin/photo_add");
        mav.addObject("photoTypeList", photoService.getPhotoTypeList());

        HttpSession session = request.getSession();
        if (null == session.getAttribute(ApplicationConstant.SESSION_PHOTO_UPLOAD_LIST)) {
            session.setAttribute(ApplicationConstant.SESSION_PHOTO_UPLOAD_LIST, new ArrayList<Photo>());
        } else {
            List<Photo> list = (List<Photo>) session.getAttribute(ApplicationConstant.SESSION_PHOTO_UPLOAD_LIST);
            list.clear();
        }

        return mav;
    }

    @RequestMapping(value = "/manage/list", method = RequestMethod.GET)
    public ModelAndView forwardPhotoListPage() {
        ModelAndView mav = new ModelAndView("admin/photo_list");
        List<TypeCount> psu = photoService.getPhotoTypeCount();
        mav.addObject("typeList", psu);
        return mav;
    }

    @RequestMapping(value = "/manage/detail", method = RequestMethod.GET)
    public ModelAndView forwardPhotoImageListPage(String albumId) {
        ModelAndView mav = new ModelAndView("admin/photo_detail");
        PageSplitUtil<Photo> psu = photoService.getPhotoListForPage(0, 999, albumId);
        mav.addObject("pagination", psu);
        return mav;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView savePhotoInfo(String photoId, String title, String type, HttpServletRequest request) {

        List<Photo> list = (List<Photo>) request.getSession().getAttribute(ApplicationConstant.SESSION_PHOTO_UPLOAD_LIST);

        PhotoForm photoForm = new PhotoForm();
        photoForm.setTitle(title);
        photoForm.setType(type);
        photoForm.setContent(list.isEmpty() ? StringUtils.EMPTY : list.toString());

        List<FormAlert> resultList = photoService.validatePhotoForm(photoForm);

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (!resultList.isEmpty()) {
            returnMap.put("result", false);
            returnMap.put("msg", resultList);
        } else {
            try {

                Long id = photoService.savePhotoInfo(photoId, title, type, list);
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
    @RequestMapping(value = "/upload/chunk/submit", method = RequestMethod.POST)
    @ResponseBody
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

                    LOGGER.info("fileItem==" + fileItem);
                }


                // session中的参数设置获取是我自己的原因,文件名你们可以直接使用fileName,这个是原来的文件名
                String fileSysName = String.valueOf(System.currentTimeMillis());
                LOGGER.info("=====================>" + fileName);

                String realname = fileSysName + fileName.substring(fileName.lastIndexOf("."));// 转化后的文件名

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String dateFormatStr = sdf.format(new Date());

                String filePath = "D:\\dev\\apache-tomcat-7.0.67\\upload\\image\\webuploader\\photo\\" + dateFormatStr + "\\";// 文件上传路径
                if (EnvUtil.isProdEnv()) {
                    filePath = "/app/data/static/webuploader";
                }

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
                    LOGGER.info("===============>done");

                    List<Photo> photoList = (List<Photo>) request.getSession().getAttribute(ApplicationConstant.SESSION_PHOTO_UPLOAD_LIST);

                    Photo photo = new Photo();
                    photo.setFilePath(destTempFile.getPath());

                    photo.setUrlPath(EnvUtil.getUrl() + "webuploader/" + dateFormatStr + destTempFile.getName());
                    photoList.add(photo);

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
