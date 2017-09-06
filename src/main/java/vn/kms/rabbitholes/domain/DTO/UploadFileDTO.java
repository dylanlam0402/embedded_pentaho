package vn.kms.rabbitholes.domain.DTO;

/**
 * @author kietlam
 */
public class UploadFileDTO {
    Long id;
    String token;


    public UploadFileDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
