package de.hfu.residents.service;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(value = Parameterized.class)
public class BaseResidentServiceEasyMockTest {

    private DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private ResidentRepository residentRepositoryMock;
    private BaseResidentService baseResidentService = new BaseResidentService();

    private Resident resident1;
    private Resident resident2;
    private Resident resident3;


    public BaseResidentServiceEasyMockTest(String[] resident1, String[] resident2, String[] resident3) throws ParseException {
        this.resident1 = new Resident(resident1[0], resident1[1], resident1[2], resident1[3], format.parse(resident1[4]));
        this.resident2 = new Resident(resident2[0], resident2[1], resident2[2], resident2[3], format.parse(resident2[4]));
        this.resident3 = new Resident(resident3[0], resident3[1], resident3[2], resident3[3], format.parse(resident3[4]));
        List<Resident> residents = Arrays.asList(this.resident1, this.resident2, this.resident3);
        this.residentRepositoryMock = createMock(ResidentRepository.class);
        expect(residentRepositoryMock.getResidents()).andReturn(residents);
        replay(residentRepositoryMock);
        baseResidentService.setResidentRepository(residentRepositoryMock);
    }

    @Parameterized.Parameters
    public static Collection daten() {
        return Arrays.asList(new String[][][] {
                {{"johann", "johannson", "goethestraße", "darmstadt", "12-12-1985"},
                        {"kerstin", "rau", "friedrichstraße", "köln", "10-03-1997"},
                        {"markus", "müller", "teststraße", "teststadt", "02-05-1977"}}
        });
    }

    /**
     * Method: getUniqueResident(Resident filterResident)
     */
    @Test(expected = ResidentServiceException.class, timeout = 1000)
    public void testGetUniqueResidentWithWildcard() throws Exception {
        Resident resident = new Resident("Te*", "", "", "", new Date());
        baseResidentService.getUniqueResident(resident);
        verify(residentRepositoryMock);
    }

    /**
     * Method: getUniqueResident(Resident filterResident)
     */
    @Test(expected = ResidentServiceException.class, timeout = 1000)
    public void testGetUniqueResidentNoResult() throws Exception {
        Resident resident = new Resident("Te", "", "", "", new Date());
        baseResidentService.getUniqueResident(resident);
        verify(residentRepositoryMock);
    }

    /**
     * Method: getUniqueResident(Resident filterResident)
     */
    @Test
    public void testGetUniqueResident() throws Exception {
        Resident resident = new Resident("johann", "johannson", "goethestraße", "darmstadt", format.parse("12-12-1985"));
        Assert.assertThat(resident.getFamilyName(), equalTo(baseResidentService.getUniqueResident(resident).getFamilyName()));
        verify(residentRepositoryMock);
    }

    /**
     * Method: getFilteredResidentsList(Resident filterResident)
     */
    @Test
    public void testGetFilteredResidentsListWithWildcard() throws Exception {
        Resident resident = new Resident("", "m*", "", "", null);
        Assert.assertThat(Arrays.asList(resident1, resident2), equalTo(baseResidentService.getFilteredResidentsList(resident)));
        verify(residentRepositoryMock);
    }

    /**
     * Method: getFilteredResidentsList(Resident filterResident)
     */
    @Test
    public void testGetFilteredResidentsList() throws Exception {
        Resident resident = new Resident("", "johannson", "", "", null);
        Assert.assertThat(Arrays.asList(resident1), equalTo(baseResidentService.getFilteredResidentsList(resident)));
        verify(residentRepositoryMock);
    }

    /**
     * Method: getFilteredResidentsList(Resident filterResident)
     */
    @Test
    public void testGetFilteredResidentsListNoResult() throws Exception {
        Resident resident = new Resident("noname", "", "", "", null);
        Assert.assertThat(Arrays.asList(), equalTo(baseResidentService.getFilteredResidentsList(resident)));
        verify(residentRepositoryMock);
    }
} 
