package com.agrovani.backend.config;

import com.agrovani.backend.entity.Crop;
import com.agrovani.backend.repository.CropRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CropDataSeeder {

    @Bean
    public ApplicationRunner seedCrops(CropRepository cropRepository) {
        return args -> {
            List<Crop> defaults = List.of(
                    crop("JOWAR", "Jowar (Sorghum)", "ज्वारी", "CEREAL"),
                    crop("BAJRA", "Bajra (Pearl Millet)", "बाजरी", "CEREAL"),
                    crop("WHEAT", "Wheat", "गहू", "CEREAL"),
                    crop("POMEGRANATE", "Pomegranate", "डाळिंब", "FRUIT"),
                    crop("GRAPES", "Grapes", "द्राक्ष", "FRUIT"),
                    crop("SUGARCANE", "Sugarcane", "ऊस", "CASH_CROP"),
                    crop("COTTON", "Cotton", "कापूस", "CASH_CROP"),
                    crop("ONION", "Onion", "कांदा", "VEGETABLE"),
                    crop("TUR", "Tur (Pigeon Pea)", "तूर", "PULSE"),
                    crop("SOYBEAN", "Soybean", "सोयाबीन", "OILSEED")
            );

            for (Crop c : defaults) {
                if (!cropRepository.existsByCode(c.getCode())) {
                    cropRepository.save(c);
                }
            }
        };
    }

    private Crop crop(String code, String nameEn, String nameMr, String category) {
        Crop c = new Crop();
        c.setCode(code);
        c.setNameEn(nameEn);
        c.setNameMr(nameMr);
        c.setCategory(category);
        return c;
    }
}