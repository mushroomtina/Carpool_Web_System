package com.example.demo.service.Algo;


import java.util.ArrayList;
import java.util.List;

public class Route {
    //parse the json date of googleMap API query
    public A[] geocoded_waypoints;
    class A{
        public String geocoder_status;
        public String place_id;
        public List<String> types;
    }
    public B[] routes;
    class B{
        public B_A bounds;
        class B_A{
            public Coord northeast;
            public Coord southwest;

        }
        public String copyrights;
        public B_B[] legs;
        class B_B{
            public Mi distance;
            public Mi duration;
            public String end_address;
            public Coord end_location;
            public String start_address;
            public Coord start_location;
            public B_B_A[] steps;
            class B_B_A{
                public Mi distance;
                public Mi duration;
                public Coord end_location;
                public String html_instructions;
                public String maneuver;
                public B_B_B_A polyline;
                class B_B_B_A{
                    public String points;
                }
                public Coord start_location;
                public String travel_mode;
            }
            public B_B_B[] traffic_speed_entry;
            public B_B_C[] via_waypoint;
            class B_B_B{}
            class B_B_C{}
        }
        public B_C overview_polyline;
        class B_C{
            public String points;
        }
        public String summary;
        public ArrayList<Integer> warnings;
        public ArrayList<Integer> waypoint_order;

    }
    public String status;
    class Coord{
        public double lat;
        public double lng;
    }
    class Mi{
        public String text;
        public long value;
    }
}
