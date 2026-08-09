class Application{
    SQLDB sd;
    MongoDB md;
    //Application directly depends on concrete classes:

    public Application(){
    }

    public void saveToSQLDB(String data){
        sd.save(); //tight coupling
    }
    public void saveToMongoDB(String data){
        md.save();
    }

}

class SQLDB{
    public void save(){
        System.out.println("saved to SQL");
    }
}

class MongoDB{
    public void save(){
        System.out.println("saved to mongodb");
    }
}

public class DIPVoilated {
    public static void main(String[] args) {
        Application application = new Application();
        application.saveToMongoDB("Pavan");
        application.saveToSQLDB("Govinda");
    }

}


