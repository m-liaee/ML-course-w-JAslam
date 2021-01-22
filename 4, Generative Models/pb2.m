%UCI-data set
clc; 

crx_conf_addr = 'crx\crx.config'; 
crx_data_addr = '\crx\crx.data'; 
crx_data = parser(crx_conf_addr, crx_data_addr); 
display(crx_data); 


% vote_conf_addr = '..\vote\vote.config'; 
% vote_data_addr = '\vote\vote.data'; 
% vote_data = parser(vote_conf_addr, vote_data_addr); 