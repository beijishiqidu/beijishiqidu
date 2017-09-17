package personal.blog.vo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_photo")
public class Photo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = PhotoAlbum.class)
    @JoinColumn(name = "album")
    private PhotoAlbum album;
    
    @Column(name = "file_path")
    private String filePath;

    @Column(name = "url_path")
    private String urlPath;

    @Column(name = "create_date")
    private Calendar createDate;

    @Column(name = "update_date")
    private Calendar updateDate;

    @Column(name = "scan_times")
    private Long scanTimes;
    
    @Column(name="face")
    private Boolean face;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public Long getScanTimes() {
        return scanTimes;
    }

    public void setScanTimes(Long scanTimes) {
        this.scanTimes = scanTimes;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public PhotoAlbum getAlbum() {
        return album;
    }

    public void setAlbum(PhotoAlbum album) {
        this.album = album;
    }

    public Boolean getFace() {
        return face;
    }

    public void setFace(Boolean face) {
        this.face = face;
    }
}
