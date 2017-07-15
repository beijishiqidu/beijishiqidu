package personal.blog.enums;

public enum GlobalValueKey {

    /**
     * image文件以及css文件，js文件的前缀路径.
     */
    URL_PATH("URL_PATH");

    private String key;

    private GlobalValueKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


}
