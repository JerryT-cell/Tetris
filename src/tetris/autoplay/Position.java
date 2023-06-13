package tetris.autoplay;

public class Position {

    private int columnPosition;
    private int rotation;
    private double score;

    public Position(int pos,int rotation,double score){
        this.columnPosition = pos;
        this.rotation = rotation;
        this.score = score;
    }
    

    /**
     * @return int return the position
     */
   

    /**
     * @param position the position to set
     */
   

    /**
     * @return int return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(double score) {
        this.score = score;
    }
    public int getRotation(){
       return rotation;
    }


    /**
     * @return int return the columnPosition
     */
    public int getColumnPosition() {
        return columnPosition;
    }

    /**
     * @param columnPosition the columnPosition to set
     */
    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

}
