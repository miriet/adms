package postech.adms.common.upload;

import postech.adms.common.persistence.FieldCopy;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by miriet on 2017. 5. 28..
 */
public class PodoUser {

    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    @FieldCopy(isCopy = true)
    private String name;

    @Column(name = "NATION")
    @FieldCopy(isCopy = true)
    private String nationality;

    @Column(name = "BIRTHDAY")
    @FieldCopy(isCopy = true)
    private String birthday;

    @Column(name = "EMAIL")
    @FieldCopy(isCopy = true)
    private String email;

    @Column(name = "SEX")
    @FieldCopy(isCopy = true)
    private String gender;

    @Column(name = "hphone")
    @FieldCopy(isCopy = true)
    private String phone;

    @Column(name = "hphone")
    @FieldCopy(isCopy = true)
    private String mobile;

    @Column(name = "created_at")
    @FieldCopy(isCopy = true)
    private Date createdDate;

    @Column(name = "updated_at")
    @FieldCopy(isCopy = true)
    private Date lastUpdatedDate;


    @Column(name = "BS_ID")
    @FieldCopy(isCopy = true)
    private String bsId;

    @Column(name = "BS_DEPT")
    @FieldCopy(isCopy = true)
    private int bsDept;

    @Column(name = "BS_COME_YEAR")
    @FieldCopy(isCopy = true)
    private String bsEntranceYear;

    @Column(name = "BS_YEAR")
    @FieldCopy(isCopy = true)
    private String bsGraduationYear;

    @Column(name = "MS_ID")
    @FieldCopy(isCopy = true)
    private String msId;

    @Column(name = "MS_DEPT")
    @FieldCopy(isCopy = true)
    private int msDept;

    @Column(name = "MS_COME_YEAR")
    @FieldCopy(isCopy = true)
    private String msEntranceYear;

    @Column(name = "MS_YEAR")
    @FieldCopy(isCopy = true)
    private String msGraduationYear;

    @Column(name = "PH_ID")
    @FieldCopy(isCopy = true)
    private String phdId;

    @Column(name = "PH_DEPT")
    @FieldCopy(isCopy = true)
    private int phdDept;

    @Column(name = "PH_COME_YEAR")
    @FieldCopy(isCopy = true)
    private String phdEntranceYear;

    @Column(name = "PH_YEAR")
    @FieldCopy(isCopy = true)
    private String phdGraduationYear;

    @Column(name = "UNITY_ID")
    @FieldCopy(isCopy = true)
    private String unityId;

    @Column(name = "UNITY_DEPT")
    @FieldCopy(isCopy = true)
    private int unityDept;

    @Column(name = "UNITY_COME_YEAR")
    @FieldCopy(isCopy = true)
    private String unityEntranceYear;

    @Column(name = "UNITY_YEAR")
    @FieldCopy(isCopy = true)
    private String unityGraduationYear;

    @Column(name = "PAMTIP_ID")
    @FieldCopy(isCopy = true)
    private String pamtipId;

    @Column(name = "PAMTIP_DEPT")
    @FieldCopy(isCopy = true)
    private int pamtipDept;

    @Column(name = "PAMTIP_COME_YEAR")
    @FieldCopy(isCopy = true)
    private String pamtipEntranceYear;

    @Column(name = "PAMTIP_YEAR")
    @FieldCopy(isCopy = true)
    private String pamtipGraduationYear;

}
