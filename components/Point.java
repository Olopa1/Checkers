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

    public void changePoint(int newWidth,int newHeight){
        this.height = newHeight;
        this.width = newWidth;
    }

    @Override
    public int compareTo(Point o) {
        int widthComparison = Integer.compare(this.width, o.width);
        if (widthComparison != 0) {
            return widthComparison;
        }
        return Integer.compare(this.height, o.height);
    }
}
