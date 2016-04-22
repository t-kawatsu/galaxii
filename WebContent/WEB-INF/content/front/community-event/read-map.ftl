<div id="map_canvas"></div>

<script type="text/javascript" 
	src="http://maps.google.com/maps/api/js?sensor=false&language=ja&callback=onMaploadComplete"></script>
	
<script type="text/javascript">
	function onMaploadComplete() {
		$('#map_canvas').show();
		map = new my.Map("map_canvas");
		map.create();
		map.putMarker(${communityEvent.lat}, ${communityEvent.lon});
	};
</script>
