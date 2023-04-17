package org.example.components.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.example.components.item.Item;
import org.example.components.item.ItemDto;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    private Long id;
    private String name;
    private String averageSize;
    private String purpose;
    private List<ItemDto> items;

    public Category toCategory(){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setPurpose(purpose);
        category.setAverageSize(averageSize);
        return category;
    }

    public static CategoryDto fromCategory(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setPurpose(category.getPurpose());
        categoryDto.setAverageSize(category.getAverageSize());
        categoryDto.setName(category.getName());
        List<ItemDto> items = new ArrayList<>();
        for (Item item : category.getItems()){
            ItemDto itemDto = ItemDto.fromItem(item);
            itemDto.setKind("[]");
            itemDto.setCategory("[]");
            items.add(itemDto);
        }
        categoryDto.setItems(items);
        return categoryDto;
    }

}
