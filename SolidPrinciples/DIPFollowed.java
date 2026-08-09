class Application{
    PersistenceDB p;

    public Application(PersistenceDB p){ //Dependency Injection.
        this.p = p;
    }
    public void saveToDB(String s){
        p.save(s);
    }
}

interface PersistenceDB{ //High-level modules should not depend directly on low-level modules. Both should depend on abstractions.
    void save(String name);
}

class MongoDB implements PersistenceDB{
    public void save(String name){
        System.out.println("name save to MongoDB");
    }
}
class SQLDB implements PersistenceDB{
    public void save(String name){
        System.out.println("name save to SQLDB");
    }
}
public class DIPFollowed {
    public static void main(String[] args) {
        //write logic
        
        // Using MongoDB
        PersistenceDB mongoDB = new MongoDB();

        Application mongoApplication =
                new Application(mongoDB); //Give me something that implements PersistenceDB

        mongoApplication.saveToDB("Pavan");


        // Using SQLDB
        PersistenceDB sqlDB = new SQLDB();

        Application sqlApplication =
                new Application(sqlDB);

        sqlApplication.saveToDB("Govinda");
    }

}
