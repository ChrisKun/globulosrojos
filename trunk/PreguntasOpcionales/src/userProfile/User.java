package userProfile;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class User implements CaseComponent {
	public enum Gender {
		Male, Female
	};

	public enum Occupation {
		administrator, artist, doctor, educator, engineer, entertainment,
		executive, healthcare, homemaker, lawyer, librarian, marketing, none,
		other, programmer, retired, salesman, scientist, student, technician, writer
	}

	Integer id;
	Integer age;
	Gender gender;
	Occupation occupation;
	String zipCode;
	//TODO decidir que datos necesitamos para el perfil de usuario

	public String toString() {
		return id + "," + age + "," + gender + "," + occupation + "," + zipCode;
	}

	/**
	 * @return Returns the age.
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            The age to set.
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return Returns the gender.
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            The gender to set.
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return Returns the id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Returns the occupation.
	 */
	public Occupation getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            The occupation to set.
	 */
	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return Returns the zipCode.
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            The zipCode to set.
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Attribute getIdAttribute() {
		return new Attribute("id", User.class);
	}

}
