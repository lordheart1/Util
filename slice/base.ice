#ifndef BASE
#define BASE

module com
{
	module kdy
	{
		module base 
		{
			dictionary<string, string> Row;
			
			["java:type:java.util.LinkedList"]
			sequence<Row> RowList;
			
			dictionary<string, string> Params;
			
			sequence<string> Ids;
			
			["java:getset"]			
			struct PageBean
			{
				/**
				*	当前页数，从1开始.
				*/
				int page;
				/**
				*	分页数
				*/
				int rows;
				long count;
				
				RowList data;
				Row footer; 
			};
			
			interface NodeQuery 
			{
				PageBean findPage(int cid,string queryId,Params param,int page,int rows);
				
				PageBean find(int cid,string queryId,Params param);
				
				PageBean findBean(int cid,string queryId,Params param,Ids idList);
			};
		};
	
	};
};
#endif