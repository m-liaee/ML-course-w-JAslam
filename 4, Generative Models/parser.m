function data = parser(config_file_addr, data_file_addr) 

    fID = fopen(config_file_addr); 
    line = fgets(fID); 
    line = char(line); 
    temp = strread(line,'%s','delimiter',' '); 
  


    n = str2num(char(temp(1)));  
     
    dis_attr_num = str2num(char(temp(2))); 
    cont_attr_num = str2num(char(temp(3))); 
     
     display(dis_attr_num); 
    

    
 
    
    data = []; 
end