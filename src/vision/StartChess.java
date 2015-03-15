package vision;


public class StartChess {
    public static int ChooseChess(int x1,int x2,int y1,int y2,int x3,int y3){
    	int a = 3;
		if((Math.abs(x1-x2)<3)&&(Math.abs(x1-x3)<3)&&(Math.abs(x3-x2)<3)&&(Math.abs(y1-y2)<3)&&(Math.abs(y1-y3)<3)
				&&(Math.abs(y3-y2)<3)){
		//1
			if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
				if((Math.abs(x3-x1)==2)&&(Math.abs(y3-y1)==2)){
					if((Math.abs(x3-x2)==1)&&(Math.abs(y3-y2)==1)){
						a = 3;
						}
					}
				}
		
		//2
		    if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
			    if((Math.abs(x1-x3)==2)||(Math.abs(y1-y3)==2)){
				    if((Math.abs(x3-x2)==0)||(Math.abs(y3-y2)==0)){
					    a = 1;
				    }
			    }
		    }
		
		//3
		    if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
			    if((Math.abs(x1-x3)==1)||(Math.abs(y1-y3)==1)){
				    if((Math.abs(x3-x2)==1)||(Math.abs(y3-y2)==1)){
					    a = 1;
				    }
			    }
		    }
		//4
		    if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
			    if((Math.abs(x1-x3)==0)||(Math.abs(y1-y3)==0)){
				    if((Math.abs(x3-x2)==1)&&(Math.abs(y3-y2)==1)){
					    a = 1;
				    }
			    }
		    }
		//5
		    if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
			    if((Math.abs(x1-x3)==1)&&(Math.abs(y1-y3)==1)){
				    if((Math.abs(x3-x2)==0)||(Math.abs(y3-y2)==0)){
					    a = 1;
				    }
			    }
		    }
		//6
		    if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
			    if((Math.abs(x1-x3)==0)||(Math.abs(y1-y3)==0)){
				    if((Math.abs(x3-x2)==2)||(Math.abs(y3-y2)==2)){
					    a = 1;
				    }
			    }
		    }
		//7
		    if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
			    if((Math.abs(x1-x3)==1)&&(Math.abs(y1-y3)==1)){
				    if((Math.abs(x2-x3)==2)&&(Math.abs(y3-y2)==2)){
					    a = 3;
				    }
			    }
		    }
		//8
		    if(((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==0))||((Math.abs(x1-x2)==0)&&(Math.abs(y1-y2)==1))){
			    if((Math.abs(x1-x3)==2)||(Math.abs(y1-y3)==2)){
				    if((Math.abs(x3-x2)==0)||(Math.abs(y3-y2)==0)){
					    a = 3;
				    }
			    }
		    }
		//9
		    if(((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==0))||((Math.abs(x1-x2)==0)&&(Math.abs(y1-y2)==1))){
			    if((Math.abs(x1-x3)==2)||(Math.abs(y1-y3)==2)){
				    if((Math.abs(x3-x2)==1)&&(Math.abs(y3-y2)==1)){
					    a = 3;
				    }
			    }
		    }
		//10
		    if(((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==0))||((Math.abs(x1-x2)==0)&&(Math.abs(y1-y2)==1))){
			    if((Math.abs(x1-x3)==1)&&(Math.abs(y1-y3)==1)){
				    if((Math.abs(x3-x2)==0)||(Math.abs(y3-y2)==0)){
					    a = 1;
				    }
			    }
		    }
		//11
		    if(((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==0))||((Math.abs(x1-x2)==0)&&(Math.abs(y1-y2)==1))){
			    if((Math.abs(x1-x3)==0)||(Math.abs(y1-y3)==0)){
				    if((Math.abs(x3-x2)==1)&&(Math.abs(y3-y2)==1)){
					    a = 1;
				    }
			    }
		    }
		//12
		    if(((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==0))||((Math.abs(x1-x2)==0)&&(Math.abs(y1-y2)==1))){
			    if((Math.abs(x1-x3)==1)&&(Math.abs(y1-y3)==1)){
				    if((Math.abs(x3-x2)==2)||(Math.abs(y3-y2)==2)){
					    a = 3;
				    }
			    }
		    }
		//13
		    if(((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==0))||((Math.abs(x1-x2)==0)&&(Math.abs(y1-y2)==1))){
			    if((Math.abs(x1-x3)==1)||(Math.abs(y1-y3)==1)){
				    if((Math.abs(x3-x2)==2)||(Math.abs(y3-y2)==2)){
					    a = 3;
				    }
			    }
		    }
		//14
		    if( ((x1+x3==2*x2) && (y2+y3==2*y1)) || ((y1+y3==2*y2) && (x2+x3==2*x1))){
		    	if((Math.abs(x1-x2)==1)||(Math.abs(y1-y2)==1)){
		    		a = 1;
		    	}	
		    }
		    
		//15
		    if((Math.abs(x1-x3)==2)&&(Math.abs(y1-y3)==2)){
		    	if( ( (x1+x3==2*x2) && (y1+y3!=2*y2) ) || ( (y1+y3==2*y2) && (x1+x3!=2*x2) ) ){
		    		a = 3;
		    	}
		    }
		//16
		    if(((Math.abs(x3-x2)==3)&&(Math.abs(y3-y2)==2))||((Math.abs(x3-x2)==2)&&(Math.abs(y1-y2)==3))){
		    	if((Math.abs(x1-x2)==1)&&(Math.abs(y1-y2)==1)){
		    		if(((Math.abs(x3-x1)==1)&&(Math.abs(y1-y3)==2))||((Math.abs(x1-x3)==2)&&(Math.abs(y1-y3)==2))){
		    			a = 1;
		    		}
		    		
		    	}
		    }
		    else{
			    a = 3;
		    }
		}
		return a;
		
	}
}