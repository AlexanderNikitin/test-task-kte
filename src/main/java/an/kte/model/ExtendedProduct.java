package an.kte.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExtendedProduct extends Product {
    protected Double avgReview;
    protected List<ReviewValueCount> reviewValueCount;
    protected Long currentReview;
}
