package sensorsystem;



public class Sensor {

    private int id;
    private String name,floor,room,colevel;
    private double level;
    
    
    public Sensor(String name, String floor, String room, double level) {
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = level;
        this.colevel= new Double(level).toString();
    }
    public Sensor(String name, String floor, String room, String colevel) {
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = level;
        this.level = Double.parseDouble(colevel);
    }

    public Sensor(int id, String name, String floor, String room, double level) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = level;
        this.colevel= new Double(level).toString();
    }
    public Sensor(int id, String name, String floor, String room, String colevel) {
        this.id = id;
        this.colevel = colevel;
        this.name = name;
        this.floor = floor;
        this.room = room;
        this.level = Double.parseDouble(colevel);
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public String getColevel() {
        return colevel;
    }

    public double getLevel() {
        return level;
    }

    public int getID()
    {
        return id;
    }
   
}