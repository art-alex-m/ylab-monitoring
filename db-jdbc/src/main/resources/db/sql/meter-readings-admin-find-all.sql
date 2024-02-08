select m1.*, meters.name as meter_name
from meter_readings m1
         inner join meters meters on m1.meter_uuid = meters.uuid
order by m1.period desc