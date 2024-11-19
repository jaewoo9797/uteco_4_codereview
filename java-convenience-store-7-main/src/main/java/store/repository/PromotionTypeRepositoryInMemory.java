package store.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import store.common.ErrorMessage;
import store.common.QuantityCounter;
import store.domain.product.PromotionDuration;
import store.domain.product.PromotionType;
import store.domain.product.PromotionTypeInfo;
import store.domain.promotion.NonPromotionCalculator;
import store.domain.promotion.PromotionCalculator;
import store.domain.promotion.PromotionCalculatorFactory;

public class PromotionTypeRepositoryInMemory implements PromotionTypeRepository {
    private static final String PROMOTIONS_DIRECTORY = "src/main/resources/promotions.md";
    Map<String, PromotionType> store = new HashMap<>();

    public PromotionTypeRepositoryInMemory() {
        initDefaultPromotionTypes();
        initStore();
    }

    @Override
    public PromotionType savePromotionType(PromotionType inputPromotionType) {
        store.put(inputPromotionType.getPromotionTypeInfoName(), inputPromotionType);
        return inputPromotionType;
    }

    @Override
    public Optional<PromotionType> findPromotionTypeByName(String promotionTypeName) {
        return Optional.ofNullable(store.get(promotionTypeName));
    }

    private void initStore() {
        try (var lines = Files.lines(Paths.get(PROMOTIONS_DIRECTORY))) {
            lines.skip(1)
                    .forEach(this::processLine);
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 파일 읽기 오류: " + PROMOTIONS_DIRECTORY, e);
        }
    }

    private void processLine(String line) {
        try {
            PromotionType promotionType = parsePromotionType(line);
            savePromotionType(promotionType);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("[ERROR] 잘못된 형식의 데이터: " + line + " - " + e.getMessage());
        }
    }

    private PromotionType parsePromotionType(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException(ErrorMessage.FILE_FORMAT_ERROR.getMessage());
        }
        return createPromotionType(parts);
    }

    private PromotionType createPromotionType(String[] parts) {
        String name = parts[0].trim();
        int buy;
        int get;
        LocalDate startDate;
        LocalDate endDate;

        try {
            buy = Integer.parseInt(parts[1].trim());
            get = Integer.parseInt(parts[2].trim());
            startDate = LocalDate.parse(parts[3].trim());
            endDate = LocalDate.parse(parts[4].trim());
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException(
                    ErrorMessage.FILE_FORMAT_DATA_FORMAT_ERROR.getMessage() + String.join(",", parts));
        }

        PromotionCalculator calculator = PromotionCalculatorFactory.getPromotionCalculator(buy, get);
        return new PromotionType(
                new PromotionTypeInfo(name, new QuantityCounter(buy), new QuantityCounter(get), calculator),
                new PromotionDuration(startDate, endDate));
    }

    private void initDefaultPromotionTypes() {
        PromotionType defaultPromotionType = new PromotionType(
                new PromotionTypeInfo("null", new QuantityCounter(0), new QuantityCounter(0),
                        new NonPromotionCalculator()),
                new PromotionDuration(LocalDate.MIN, LocalDate.MAX)
        );
        store.put("null", defaultPromotionType);
    }
}
