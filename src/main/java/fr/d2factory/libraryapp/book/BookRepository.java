package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import fr.d2factory.libraryapp.library.HasLateBooksException;
import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.member.Member;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository extends Member implements Library{
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    private Map<Integer, LocalDate> booksOwned = new HashMap<>();

    public void addBooks(Book book, ISBN isbnCode){
    	availableBooks.put(isbnCode, book);
    	
    }

    public Book findBook(long isbnCode) {
    	Book b = availableBooks.get(isbnCode);
        return b;
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt, Member member){
    	borrowedBooks.put(book, borrowedAt);
    	booksOwned.put(member.getiD(), borrowedAt);
    }

    public LocalDate findBorrowedBookDate(Book book) {
    	LocalDate l = borrowedBooks.get(book);
        return l;
    }
    

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		if(booksOwned.containsKey(member.getiD()) == true)
		{
			LocalDate l = booksOwned.get(member.getiD());
			long daysDifference = ChronoUnit.DAYS.between(l, LocalDate.now());
			
			if(daysDifference > 30 && member.getMemberType().equals("STUDENT"))
			{
				throw new HasLateBooksException("Return your previous book");
			}
			else if(daysDifference > 60 && member.getMemberType().equals("RESIDENT"))
			{
				throw new HasLateBooksException("Return your previous book");
			}
		}
		else
		{
			Book b = findBook(isbnCode);
			saveBookBorrow(b, LocalDate.now(), member);
			booksOwned.put(member.getiD(), LocalDate.now());
			return b;
		}
		return null;
	}

	@Override
	public void returnBook(Book book, Member member) {
		
		LocalDate l = borrowedBooks.get(book);
		long daysDifference = ChronoUnit.DAYS.between(l, LocalDate.now());
		payBook(daysDifference, member);
		
	}

	@Override
	public long payBook(long numberOfDays, Member member) {
		
		long pay = 0;
		
		if(member.getMemberType().equals("STUDENT"))
		{
			if(member.getStudentYear().equals("FIRST") && numberOfDays==15 )
			{
				System.out.println("FREE");
			}
			else
			{
				pay = (long) (numberOfDays * 0.10);
				
			}
		}
		else if(member.getMemberType().equals("RESIDENT"))
		{
			if(numberOfDays < 60)
			{
				pay = (long) (numberOfDays * 0.10);
			}
			else
			{
				pay = (long) (numberOfDays * 0.20);
			}
			
		}
		
		return pay;
		
	}
}
