interface Shape{
    void area();
    void volume();

}

class Square implements Shape{
    public void area(){
        System.out.println("area of square");
    }
    public void volume(){
        //Throw Exception
    }
}

class Rectangle implements Shape{
    public void area(){
        System.out.println("area of Rectangle");
    }
    public void volume(){
        //Throw Exception
    }
}

class Cube implements Shape{
    public void area(){
        System.out.println("area of Cube");
    }
    public void volume(){
        System.out.println("Volume of Cube");
    }
}
public class ISPVoilated{
    public static void main(String[] args) {
        //Initiate logic
    }
}