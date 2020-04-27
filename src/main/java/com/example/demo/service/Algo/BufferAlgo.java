package com.example.demo.service.Algo;

import com.example.demo.domain.Point;
import com.google.gson.Gson;
import com.vividsolutions.jts.algorithm.PointLocator;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.operation.buffer.BufferOp;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BufferAlgo {
    private Point startCoordinate;
    private Point endCoordinate;
    private Point waypoint;
    private String key = "YOUR_KEY";
    private Integer bufferDistance;
    public Point getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(Point startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public Point getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Point endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public Point getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(Point waypoint) {
        this.waypoint = waypoint;
    }

    public Integer getBufferDistance() {
        return bufferDistance;
    }

    public void setBufferDistance(Integer bufferDistance) {
        this.bufferDistance = bufferDistance;
    }

    public List<String> routePlan() throws IOException {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+ this.startCoordinate.getLatitude() +","+this.startCoordinate.getLongitude();
        url+="&destination="+ this.endCoordinate.getLatitude() +","+this.endCoordinate.getLongitude();
        url+="&key="+this.key;
        String json = "";
        URL U = new URL(url);
        URLConnection connection = U.openConnection();
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine())!= null)
        {
            json += line;
        }
        in.close();
        Gson gson=new Gson();
        Route route = gson.fromJson(json,Route.class);
        List<String> points = new ArrayList<>();
        int length = route.routes[0].legs[0].steps.length;
        for (int step=0;step<length;step++){
            String coord = "";
            coord+=Double.toString(route.routes[0].legs[0].steps[step].end_location.lng);
            coord+=" ";
            coord+=Double.toString(route.routes[0].legs[0].steps[step].end_location.lat);
            if(step!=length-1){coord+=", ";}
            points.add(coord);
        }
        return points;
    }
    public Geometry createBufferZone(List<String> coords) throws com.vividsolutions.jts.io.ParseException {
        String readText = "LINESTRING(";
        for(String coord:coords){
            readText+=coord;
        }
        readText+=")";
        GeometryFactory gf = new GeometryFactory();
        WKTReader reader = new WKTReader( gf );
        LineString line = (LineString) reader.read(readText);
        double degree = this.bufferDistance / (2 * Math.PI * 6371004) * 360;
        //create buffer zone
        BufferOp bufOp = new BufferOp(line);
        bufOp.setEndCapStyle(BufferOp.CAP_ROUND);
        Geometry bg = bufOp.getResultGeometry(degree);
        return bg;
    }
    public boolean checkInZone(Geometry bg,Point targetPoint) throws ParseException, com.vividsolutions.jts.io.ParseException {
        //check if the given point is inside the polygon zone
        double longitude = targetPoint.getLongitude();
        double latitude = targetPoint.getLatitude();
        Coordinate point = new Coordinate(longitude, latitude);
        PointLocator a = new PointLocator();
        boolean p1 = a.intersects(point, bg);
        return p1;
    }
}
