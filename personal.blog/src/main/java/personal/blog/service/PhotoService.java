package personal.blog.service;

import java.util.List;

import personal.blog.cache.TypeCount;
import personal.blog.form.FormAlert;
import personal.blog.form.PhotoForm;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.ExecResult;
import personal.blog.vo.Photo;
import personal.blog.vo.PhotoAlbum;
import personal.blog.vo.PhotoType;

public interface PhotoService {

    PageSplitUtil<Photo> getPhotoListForPage(Integer firstResult, Integer maxResults, String typeId);

    List<PhotoType> getPhotoTypeList();

    List<TypeCount> getPhotoTypeCount();

    List<FormAlert> validatePhotoForm(PhotoForm articleForm);

    Long savePhotoInfo(String photoId, String title, String type, List<Photo> list);

    List<Photo> getPhotoAlnumFaceList(String photoTypeId);

    List<PhotoAlbum> getPhotoAlbumList();

    boolean deletePhotoById(String photoId);

    List<TypeCount> getPhotoAlbumCount();

    ExecResult savePhotoTypeInfo(String typeId, String typeName);

    PhotoType getPhotoTypeById(String typeId);

    ExecResult deletePhotoTypeById(String typeId);

    PageSplitUtil<Photo> getPhotoListForPageByAlbumId(Integer firstResult, Integer maxResults, String albumId);


}
