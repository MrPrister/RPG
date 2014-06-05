package game.level;

import tools.Globals;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {

	// map object
	protected OrthogonalTiledMapRenderer level;
	private TiledMap mapFile;
	private float unitScale;
	
	// movement translation (smooth scrolling)
	protected float transX;
	protected float transY;
	protected float transStep;
	protected int lastX;
	protected int lastY;
	
	// map basics
	public int tilesWidth;
    public int tilesHeight;
	
	public Map(TiledMap mapFile, float unitScale) {
		this.mapFile = mapFile;
		this.unitScale = unitScale;
		
		level = new OrthogonalTiledMapRenderer(mapFile, unitScale);

        // speed the camera pans
        transStep = 0.002f;
	}
	
	public void render() {
		level.setView(Globals.camera.view);
		level.render();
	}
	
	public void setTransX(float x) {
		//transX = x;
		transX += x;
	}
	public void setTransY(float y) {
		//transY = y;
		transY += y;
	}
	
	public OrthogonalTiledMapRenderer getMap() {
		return level;
	}
	
	public float getTileWidth() {
		//return mapFile.getTileSets().getTile(0).getTextureRegion().getRegionWidth();
		return 40;
	}
	
	public float getTileHeight() {
		//return mapFile.getTileSets().getTile(0).getTextureRegion().getRegionWidth();
		return 40;
	}
	
	public Boolean isTileBlocked(int x, int y) {
		MapLayers layers = mapFile.getLayers();
		
		for (int i = 0; i < layers.getCount(); i++) {
			
			TiledMapTileLayer layer  = (TiledMapTileLayer) layers.get(i); 
			TiledMapTileLayer.Cell cell = layer.getCell(x, y);
			if(cell != null) {
				TiledMapTile tile = cell.getTile();
				if(tile != null) {
					if(Boolean.parseBoolean(getTileProperty(tile, "blocked", "false"))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean isTileBlocked(float x, float y) {
		return isTileBlocked((int) x, (int) y);
	}

	private String getTileProperty(TiledMapTile tile, String name, String defaultValue) {
		MapProperties properties = tile.getProperties();
		
		String value = (String) properties.get(name);
		
		if(value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}
	
}
