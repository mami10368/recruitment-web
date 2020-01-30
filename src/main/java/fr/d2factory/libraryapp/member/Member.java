package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member{
	/**
	 * Member Id
	 */
	private int iD;
	
	/**
	 *  Type of member student or residents
	 */
	private String memberType;
	
	/**
	 * Year of the student
	 */
	private String studentYear;
	
    /**
     * An initial sum of money the member has
     */
    private float wallet;

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract long payBook(long numberOfDays, Member member);
    
    

    public int getiD() {
		return iD;
	}



	public void setiD(int iD) {
		this.iD = iD;
	}



	public String getMemberType() {
		return memberType;
	}



	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}



	public String getStudentYear() {
		return studentYear;
	}



	public void setStudentYear(String studentYear) {
		this.studentYear = studentYear;
	}



	public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
}
