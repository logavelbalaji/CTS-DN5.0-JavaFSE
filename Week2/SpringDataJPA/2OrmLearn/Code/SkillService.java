import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SkillService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillService.class);

    @Autowired
    private SkillRepository skillRepository;

    public Skill get(int id) {
        LOGGER.info("Start");
        return skillRepository.findById(id).orElse(null);
    }

    public void save(Skill skill) {
        LOGGER.info("Start");
        skillRepository.save(skill);
        LOGGER.info("End");
    }
}
