function origin()
{
    var mapProp = {
        center:new google.maps.LatLng(51.508742,-0.120850),
        zoom:5,
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };
    var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
}

google.maps.event.addDomListener(window, 'load', origin);

$(document).ready(function(){

    $("#pickStart").click(function(){
        $("#googleMap").slideDown("slow");
    });
    $("#pickStart").dblclick(function(){
        $("#googleMap").slideUp("slow");
    });
});

$(document).ready(function(){
    $("#endStart").click(function(){
        $("#googleMap2").slideDown("slow");
    });
    $("#endStart").dblclick(function(){
        $("#googleMap2").slideUp("slow");
    });
});

var map;
var myCenter=new google.maps.LatLng(43.7,-79.6);

function initialize()
{
    var mapProp = {
        center:myCenter,
        zoom:10,
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
    map2 = new google.maps.Map(document.getElementById("googleMap2"),mapProp);

    google.maps.event.addListener(map, 'click', function(event) {
        if(window.confirm("Do you want to choose this location?")) {
            var location = event.latLng.lat()+" "+event.latLng.lng();
            document.getElementById("start").value = location;
            // window.alert(location);
        }else {
            window.alert("Choose again")
        }
    });

    google.maps.event.addListener(map2, 'click', function(event) {
        if(window.confirm("Do you want to choose this location?")) {
            var location = event.latLng.lat()+" "+event.latLng.lng();
            document.getElementById("end").value = location;
            // window.alert(location);
        }else {
            window.alert("Choose again")
        }
    });
}

// function placeMarker(location) {
//
//     var marker = new google.maps.Marker({
//         position: location,
//         map: map,
//     });
//     var infowindow = new google.maps.InfoWindow({
//         content: 'Latitude: ' + location.lat() + '<br>Longitude: ' + location.lng()
//     });
//     infowindow.open(map,marker);
// }

google.maps.event.addDomListener(window, 'load', initialize);