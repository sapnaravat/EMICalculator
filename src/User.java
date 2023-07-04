public class User {
    private  String username;
    private  String adminName;
    private boolean isAdmin;

    public User(String username, boolean isAdmin, String adminName){
        this.username = username;
        this.adminName = adminName;
        this.isAdmin = isAdmin;
    }
    public  String getUsername(){
        return  username;
    }
    public  String getAdminName(){
        return adminName;
    }
    public boolean isAdmin(){
        return isAdmin;
    }
}
