package edu.scu.lwang.finalprojectscene;

/**
 * Created by Ruben on 6/4/2016.
 */
public class GridItem {

    private Plant plant;
    private String content;
    private String imageResource;

    public GridItem(String content, String imageResource, Plant plant) {
        this.content = content;
        this.imageResource = imageResource;
        this.plant = plant;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
