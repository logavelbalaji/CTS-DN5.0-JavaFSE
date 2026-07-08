import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country findCountryByCode(String code) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(code);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new CountryNotFoundException("Country not found");
        }
    }

    public void addCountry(Country country) {
        countryRepository.save(country);
    }

    public void updateCountry(String code, String name) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(code);
        if (result.isPresent()) {
            Country country = result.get();
            country.setName(name);
            countryRepository.save(country);
        } else {
            throw new CountryNotFoundException("Country not found");
        }
    }

    public void deleteCountry(String code) {
        countryRepository.deleteById(code);
    }

    public List<Country> findCountryByPartialName(String name) {
        return countryRepository.findByNameContaining(name);
    }

    public List<Country> findCountryByPartialNameSorted(String name) {
        return countryRepository.findByNameContainingOrderByNameAsc(name);
    }

    public List<Country> findCountryByStartingAlphabet(String alphabet) {
        return countryRepository.findByNameStartingWith(alphabet);
    }
}
