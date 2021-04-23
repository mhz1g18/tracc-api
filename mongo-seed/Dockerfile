FROM mongo

COPY roles.json /roles.json
COPY exercises.json /exercises.json
COPY foods.json /foods.json
CMD mongoimport --host trakkdb --db tracc2 --collection roles --type json --file /roles.json --jsonArray && mongoimport --host trakkdb --db tracc2 --collection exercises --type json --file /exercises.json --jsonArray && mongoimport --host trakkdb --db tracc2 --collection nutrition --type json --file /foods.json --jsonArray