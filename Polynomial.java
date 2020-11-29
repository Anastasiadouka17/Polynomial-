package assignment2;

import java.math.BigInteger;

public class Polynomial implements DeepClone<Polynomial>
{
	private class AnastasiasPointer<E>{
		private AnastasiasPointer<E> next=null;
		private E value;
		public void add(E e) {
			if (value == null) {
				value = e;
			} else {
				AnastasiasPointer<E> newnode = new AnastasiasPointer<E>();
				newnode.next = next;
				newnode.value = e;
				next = newnode;
			}
		}
	}
	
//	private class AnastasiasTerm extends Term{
//		 public AnastasiasTerm(int e, BigInteger c) {
//			super(e, c);
//		}
//		private Term next=null;
//
//		public Term getNext() {
//			// TODO Auto-generated method stub
//			return next;
//		}
//		public void setNext(Term next) {
//			this.next = next;
//		}
//	}
	
 private AnastasiasPointer<Term> anastasiaspoly;
 private SLinkedList<Term> polynomial;
 public int size()
 { 
  return polynomial.size();
 }
 private Polynomial(SLinkedList<Term> p)
 {
  polynomial = p;
  anastasiaspoly = new AnastasiasPointer<>();
  for(Term currentTerm: p) {
	  anastasiaspoly.add(currentTerm);
  }
  
 }
 
 public Polynomial()
 {
  polynomial = new SLinkedList<Term>();
  anastasiaspoly = new AnastasiasPointer<Term>();
 }
 
 // Returns a deep copy of the object.
 public Polynomial deepClone()
 { 
  return new Polynomial(polynomial.deepClone());
 }
 
 /* 
  * TODO: Add new term to the polynomial. Also ensure the polynomial is
  * in decreasing order of exponent.
  */
 public void addTerm(Term t)
 { 
	 AnastasiasPointer<Term> temp = anastasiaspoly;
  int position = 0;
  for (Term currentTerm: polynomial) {
	  if(t.getExponent()>currentTerm.getExponent()) {
		  this.polynomial.add(position, t);
		  anastasiaspoly.add(t);
		  return;
	  }
	  else if (t.getExponent()==currentTerm.getExponent()) {
		  currentTerm.setCoefficient(t.getCoefficient().add(currentTerm.getCoefficient()));
		  return;
	  }
	  position ++;
	  temp = temp.next;
	  
  }
  this.polynomial.addLast(t);
  this.anastasiaspoly.add(t);
  // Hint: Notice that the function SLinkedList.get(index) method is O(n), 
  // so if this method were to call the get(index) 
  // method n times then the method would be O(n^2).
  // Instead, use a Java enhanced for loop to iterate through 
  // the terms of an SLinkedList.
  /*
  for (Term currentTerm: polynomial)
  {
   // The for loop iterates over each term in the polynomial!!
   // Example: System.out.println(currentTerm.getExponent()) should print the exponents of each term in the polynomial when it is not empty.  
  }
  */
 }
 
 public Term getTerm(int index)
 {
  return polynomial.get(index);
 }
 
 //TODO: Add two polynomial without modifying either
 public static Polynomial add(Polynomial p1, Polynomial p2)
 {
//  Polynomial p3 = p1.deepClone();
//  for (Term currentTerm: p2.polynomial) {
//	  p3.addTerm(currentTerm);
	  
//  }
//  return p3;
	 AnastasiasPointer<Term> start1 = p1.anastasiaspoly;
	 AnastasiasPointer<Term> start2 = p2.anastasiaspoly;
	 Polynomial p3= new Polynomial();
	 while (!(start1 == null) && !(start2 ==null)) {
			 
		 if ( start1.value.getExponent()==start2.value.getExponent()) {
			 p3.addTerm(new Term(start1.value.getExponent(),start1.value.getCoefficient().add(start2.value.getCoefficient())));
			 start1 = start1.next;
			 start2 = start2.next;
		 }
		 else if( start1.value.getExponent()>start2.value.getExponent()) {
			 p3.addTerm(start1.value);
			 start1 = start1.next;
			 
		 }
		 else {
			 p3.addTerm(start2.value);
			 start2 = start2.next;
			 
			 
		 }
		 
	 }
	 if (start1 ==null) {
		 while(start2!=null) {
			 p3.addTerm(start2.value);
			 start2 = start2.next;
		 }
		 
	 }
	 if (start2 ==null) {
		 while(start1!=null) {
			 p3.addTerm(start1.value);
			 start1 = start1.next;
		 }
	 }
	return p3;	 
 }
 
 
 //TODO: multiply this polynomial by a given term.
 public void multiplyTerm(Term t)
 { 
  for (Term currentTerm: polynomial) { 
	  currentTerm.setCoefficient(currentTerm.getCoefficient().multiply(t.getCoefficient()));
	  currentTerm.setExponent(currentTerm.getExponent() + t.getExponent());
  }
 }
 
 //TODO: multiply two polynomials
 public static Polynomial multiply(Polynomial p1, Polynomial p2)
 {
  Polynomial p3 = new Polynomial();
  for (Term currentTerm: p2.polynomial) {
	  Polynomial temp = p1.deepClone();
	  temp.multiplyTerm(currentTerm);
	  Polynomial.add(p3, temp);
  }
  
  return p3;
 }
 
 // TODO: evaluate this polynomial.
 // Hint:  The time complexity of eval() must be order O(m), 
 // where m is the largest degree of the polynomial. Notice 
 // that the function SLinkedList.get(index) method is O(m), 
 // so if your eval() method were to call the get(index) 
 // method m times then your eval method would be O(m^2).
 // Instead, use a Java enhanced for loop to iterate through 
 // the terms of an SLinkedList.

 public BigInteger eval(BigInteger x)
 {
//	 System.out.println("Poly is");
//	 for (Term currentTerm : polynomial) {
//		 System.out.println(currentTerm.getCoefficient() +" "+ currentTerm.getExponent());
//	 }
  
	 BigInteger sidelestis = new BigInteger("13");
	 int count =0;
	 int oldexponent = 13;
	for (Term currentTerm : polynomial) {
		if (count == 0) {
			sidelestis = currentTerm.getCoefficient();
			oldexponent = currentTerm.getExponent();
		}
		else {
			int y = oldexponent-currentTerm.getExponent();
			
			oldexponent = currentTerm.getExponent();
			sidelestis = currentTerm.getCoefficient().add(sidelestis.multiply(x.pow(y)));
		}
		count++;
	}
	if ( oldexponent != 0) {
		sidelestis = sidelestis.multiply(x.pow(oldexponent));
	}
	 return sidelestis;
  
 }
 
 // Checks if this polynomial is a clone of the input polynomial
 public boolean isDeepClone(Polynomial p)
 { 
  if (p == null || polynomial == null || p.polynomial == null || this.size() != p.size())
   return false;

  int index = 0;
  for (Term term0 : polynomial)
  {
   Term term1 = p.getTerm(index);

   // making sure that p is a deep clone of this
   if (term0.getExponent() != term1.getExponent() ||
     term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)  
    return false;

   index++;
  }
  return true;
 }
 
 // This method blindly adds a term to the end of LinkedList polynomial. 
 // Avoid using this method in your implementation as it is only used for testing.
 public void addTermLast(Term t)
 { 
  polynomial.addLast(t);
 }
 
 
 @Override
 public String toString()
 { 
  if (polynomial.size() == 0) return "0";
  return polynomial.toString();
 }
}
