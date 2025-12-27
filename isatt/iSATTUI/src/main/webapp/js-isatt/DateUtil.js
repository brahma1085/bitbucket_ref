var getDates=new function()
{
	var monthNames = [ "January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December" ];
	var dayNames=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
	var currentDate = new Date() 
	this.getCurrentDate=function()
	{
		return currentDate;
	};
	this.getCurrentDay=function()
	{
		return currentDate.getDate();
	};
	this.getCurrentMonth=function()
	{
		return currentDate.getMonth();
	};
	this.getCurrentYear=function()
	{
		return currentDate.getFullYear();
	};
	this.getHours=function()
	{
		return currentDate.getHours();
	};
	this.getMinutes=function()
	{
		return currentDate.getMinutes();
	};
	this.getCurrentMonthName=function(noOfLetters)
	{
		if(parseInt(noOfLetters))
		{
			return monthNames[currentDate.getMonth()].substr(0,noOfLetters);
		}
		else
		{
			return monthNames[currentDate.getMonth()];
		}
	};
	this.getCurrentDayName=function(noOfLetters)
	{
		if(parseInt(noOfLetters))
		{
			return dayNames[currentDate.getDay()].substr(0,noOfLetters);
		}
		else
		{
			return dayNames[currentDate.getDay()];
		}
	};
	this.getDay=function(passedDate)
	{
		return passedDate.getDate();
	};
	this.getMonth=function(passedDate)
	{
		return passedDate.getMonth();
	};
	this.getYear=function(passedDate)
	{
		return passedDate.getFullYear();
	};
	this.getMonthName=function(passedDate, noOfLetters)
	{
		if(parseInt(noOfLetters))
		{
			return monthNames[passedDate.getMonth()].substr(0,noOfLetters);
		}
		else
		{
			return monthNames[passedDate.getMonth()];
		}
	};
	this.getDayName=function(passedDate,noOfLetters)
	{
		if(parseInt(noOfLetters))
		{
			return dayNames[passedDate.getDay()].substr(0,noOfLetters);
		}
		else
		{
			return dayNames[passedDate.getDay()];
		}
	};
	this.getNextDate=function(passedDate,noOfDaysToIncrement)
	{
		var nextDate;
		if(parseInt(noOfDaysToIncrement))
		{
			nextDate = new Date(passedDate.getTime() + (noOfDaysToIncrement * 24 * 60 * 60 * 1000));	
		}
		else
		{
			nextDate = new Date(passedDate.getTime() + (24 * 60 * 60 * 1000));
		}
		return nextDate;
	};
	this.getPrevDate=function(passedDate,noOfDaysToIncrement)
	{
		var nextDate;
		if(parseInt(noOfDaysToIncrement))
		{
			nextDate = new Date(passedDate.getTime() - (noOfDaysToIncrement * 24 * 60 * 60 * 1000));	
		}
		else
		{
			nextDate = new Date(passedDate.getTime() - (24 * 60 * 60 * 1000));
		}
		return nextDate;
	};
	
	/*
	 * Added by Nagabhushan S Pujar on 31-Jul-2013 for getting Date in Ordinal
	 * format
	 */

	this.getDateInOrdinalFormat = function(passedDate) {
		var suffix = "";
		day = passedDate.getDate() + "";
		switch (day) {
		case '1':
		case '21':
		case '31':
			suffix = 'st';
			break;
		case '2':
		case '22':
			suffix = 'nd';
			break;
		case '3':
		case '23':
			suffix = 'rd';
			break;
		default:
			suffix = 'th';
		}
		return passedDate.getDate() + suffix + " "
				+ monthNames[passedDate.getMonth()] + ","
				+ passedDate.getFullYear();

	}

	this.getLastDayOfMonth = function(passedDate, inOrdinalFormat) {
		var y = passedDate.getFullYear(), m = passedDate.getMonth();
		var lastDay = new Date(y, m + 1, 0);
		var day = lastDay.getDate() + "";
		if (inOrdinalFormat) {
			switch (day) {
			case '1':
			case '21':
			case '31':
				suffix = 'st';
				break;
			case '2':
			case '22':
				suffix = 'nd';
				break;
			case '3':
			case '23':
				suffix = 'rd';
				break;
			default:
				suffix = 'th';
			}

			lastDay = day + suffix;
		} else {
			lastDay = day;
		}

		return lastDay;

	}
}