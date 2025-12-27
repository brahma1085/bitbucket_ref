#!/usr/bin/expect -f
# set Variables

echo $1
echo $2



#set username "c1560578@nc1-iphonesys-it2-launchpad01"
#set appname "ssh c1560578@nc1-iphonesys-it2-app001"
#set password "Suzu@123"

# now connect to remote UNIX box (ipaddr) with given script to execute
#spawn ssh $username
sudo $1
#match_max 100000
# Look for passwod prompt
expect "*?assword:*"
# Send password aka $password 
send -- "$2\r"
# send blank line (\r) to make sure we get back to gui
#send -- "\r"
#expect "*bash*"
#send -- "$appname\r"
#expect "*?(yes/no)*"
#send "yes\r"
#expect "*?assword:*"
#send -- "$password\r"
# send blank line (\r) to make sure we get back to gui
#send -- "\r"
#expect "*bash*"
#send -- "sudo -u worun -i\r"
interact