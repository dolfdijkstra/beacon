host=localhost:8080
# invoke wget to get a session cookie 
wget -S -O out.log -o header.log "http://$host/cs/Satellite?pagename=Support%2FPerformance%2FStandard%2Florem" 
cookieval=`awk 'BEGIN {FS="[ ;]"} /Set-Cookie/ { print $4 }' header.log`
rm header.log
c=10
n=30000

curl 'http://localhost:8180/cs/CatalogManager?ftcmd=flushcatalog&tablename=AssetType&authusername=ContentServer&authpassword=password'
ab -c $c -n $n -k -C "$cookieval"  'http://localhost:8180/cs/Satellite?pagename=Support/Performance/Standard/db&bcache=true&bload=true&flush=false'

curl 'http://localhost:8180/cs/CatalogManager?ftcmd=flushcatalog&tablename=AssetType&authusername=ContentServer&authpassword=password'
ab -c $c -n $n -k -C "$cookieval"  'http://localhost:8180/cs/Satellite?pagename=Support/Performance/Standard/db&bcache=false&bload=true&flush=false'

curl 'http://localhost:8180/cs/CatalogManager?ftcmd=flushcatalog&tablename=AssetType&authusername=ContentServer&authpassword=password'
ab -c $c -n $n -k -C "$cookieval"  'http://localhost:8180/cs/Satellite?pagename=Support/Performance/Standard/db&bcache=true&bload=false&flush=true'

curl 'http://localhost:8180/cs/CatalogManager?ftcmd=flushcatalog&tablename=AssetType&authusername=ContentServer&authpassword=password'
ab -c $c -n $n -k -C "$cookieval"  'http://localhost:8180/cs/Satellite?pagename=Support/Performance/Standard/db&bcache=false&bload=false&flush=true'

curl 'http://localhost:8180/cs/CatalogManager?ftcmd=flushcatalog&tablename=AssetType&authusername=ContentServer&authpassword=password'
