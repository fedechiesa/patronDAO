package test;

import dao.BD;
import dao.impl.DentistDAOH2;
import model.Dentist;
import org.junit.jupiter.api.Test;
import service.DentistService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DentistServiceTest {

    DentistService dentistService = new DentistService(new DentistDAOH2());

    @Test
    void save() {
        BD.createTables();
        Dentist dentist = new Dentist(444, "Carolina", "De Elia");

        dentistService.save(dentist);

        assertNotNull(dentist.getId());
    }

    @Test
    void findById() {
        Dentist dentist = dentistService.findById(1);
        assertNotNull(dentist);

    }

    @Test
    void update() {
        Dentist dentist = new Dentist(1,555, "Gabriel", "Costacurta");
        dentistService.update(dentist);

        assertNotNull(dentist.getId());
        assertEquals(true, dentist.getName().equals("Gabriel"));

    }

    @Test
    void delete() {
        Dentist dentistDeleted = dentistService.findById(2);
        assertNull(dentistDeleted);
    }

    @Test
    void findAll() {
        List<Dentist> dentistList = dentistService.findAll();

        assertTrue(dentistList.size() > 0);
    }
}