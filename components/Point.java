package components;

public class Point implements Comparable<Point>{
    private int width;
    private int height;

    public Point(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    @Override
    public int compareTo(Point o) {
        if(this.width == o.getWidth() && this.height == o.getHeight()){
            return 0;
        }
        return 1;
    }
}
