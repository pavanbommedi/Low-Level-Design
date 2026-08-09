interface Shape2D {
    void area();
}

interface Shape3D {
    void volume();
}


// 2D Shape
class Square implements Shape2D {

    @Override
    public void area() {
        System.out.println("Area of Square");
    }
}


// 2D Shape
class Rectangle implements Shape2D {

    @Override
    public void area() {
        System.out.println("Area of Rectangle");
    }
}


// 3D Shape
class Cube implements Shape2D, Shape3D {

    @Override
    public void area() {
        System.out.println("Surface Area of Cube");
    }

    @Override
    public void volume() {
        System.out.println("Volume of Cube");
    }
}


// Client that only needs 2D functionality
class AreaCalculator {

    public void calculateArea(Shape2D shape) {
        shape.area();
    }
}


// Client that only needs 3D functionality
class VolumeCalculator {

    public void calculateVolume(Shape3D shape) {
        shape.volume();
    }
}


public class ISPFollowed {

    public static void main(String[] args) {

        // Create shapes
        Shape2D square = new Square();
        Shape2D rectangle = new Rectangle();
        Cube cube = new Cube();

        //interface cube references
        Shape2D cubeArea = cube;
        Shape3D cubeVolume = cube;
 
        // Create calculators
        AreaCalculator areaCalculator = new AreaCalculator();
        VolumeCalculator volumeCalculator = new VolumeCalculator();

        // Calculate areas
        areaCalculator.calculateArea(square);
        areaCalculator.calculateArea(rectangle);
        areaCalculator.calculateArea(cubeArea);

        // Calculate volume
        volumeCalculator.calculateVolume(cubeVolume);
    }
}