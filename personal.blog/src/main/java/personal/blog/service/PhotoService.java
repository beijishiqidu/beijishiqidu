package personal.blog.service;

import java.util.List;

import personal.blog.cache.TypeCount;
import personal.blog.form.FormAlert;
import personal.blog.form.PhotoForm;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Photo;
import personal.blog.vo.PhotoType;

public interface PhotoService {

    PageSplitUtil<Photo> getPhotoListForPage(Integer firstResult, Integer maxResults, String typeId);

    List<PhotoType> getPhotoTypeList();

    List<TypeCount> getPhotoTypeCount();

    List<FormAlert> validatePhotoForm(PhotoForm articleForm);

    Long savePhotoInfo(String photoId, String title, String type, List<Photo> list);

    void deletePhotoById(Long photoId);

    Photo getPhotoById(Long photoId);

    List<Photo> getPhotoAlnumFaceList(String photoTypeId);


}
