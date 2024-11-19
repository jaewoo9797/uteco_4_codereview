package store.io;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.common.ErrorMessage;
import store.dto.OrderRequestDto;
import store.service.ProductManager;

public class InputView {
    private static final String ORDER_INPUT_PATTERN = "";
    private static final String DELIMITER_ORDER = ",";
    private static final String DELIMITER_PRODUCT = "-";
    private static final String PARSER_REPLACE = "[\\[\\]]";
    private static final String PARSER_SPACE = "";
    private static final String AGREE_CHARACTER = "Y";
    private static final String DISAGREE_CHARACTER = "N";
    private final ProductManager productManager;

    public InputView(ProductManager productManager) {
        this.productManager = productManager;
    }

    public List<OrderRequestDto> enterOrder() {
        List<OrderRequestDto> requestOrders = new ArrayList<>();
        ProductPresent.printRequireProductNameAndQuantity();
        String orders = Console.readLine();
        for (String order : orders.split(DELIMITER_ORDER)) {
            requestOrders.add(parseOrderToDto(order));
        }
        return requestOrders;
    }

    public void checkQuantityForOrders(List<OrderRequestDto> orderRequests) {
        productManager.checkQuantityForOrders(orderRequests);
    }

    public boolean enterAdditionalOrder() {
        ProductPresent.printAdditionalOrder();
        String order = Console.readLine();
        order = order.toUpperCase();
        return order.equals(AGREE_CHARACTER);
    }



    private OrderRequestDto parseOrderToDto(String order) {
        String orderInfo = order.replaceAll(PARSER_REPLACE, PARSER_SPACE);
        String[] parts = orderInfo.split(DELIMITER_PRODUCT);
        String productName = parts[0];
        int quantity = StringToInt(parts[1]);
        return new OrderRequestDto(productName, quantity);
    }

    private int StringToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR.getMessage());
        }
    }
}
