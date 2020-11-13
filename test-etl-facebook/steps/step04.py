from facebook_scraper import get_posts
import csv
import itertools
import io
from datetime import datetime

csv_columns = ['post_id','text','time','image','images','likes','shares',
				 'post_url', 'user_id', 'video_id',	'video', 'shared_text',
				 'video_thumbnail', 'comments', 'post_text', 'link',
				 'year', 'month', 'day', 'hour', 'min']
csv_file = "results/step04.csv"


#11/11/2020  11:57:51 PM
try:
	with io.open(csv_file, 'w', encoding="utf-8") as csvfile:
		writer = csv.DictWriter(csvfile, fieldnames=csv_columns, delimiter='~')
		writer.writeheader()
		posts = get_posts('icetexcolombia', pages=5)
		for post in posts:
			
			post['text'] = post['text'].replace('\n',' ')
			post['post_text'] = post['post_text'].replace('\n',' ')
			post['shared_text'] = post['shared_text'].replace('\n',' ')
			#d = datetime.strptime(post['time'], '%m/%d/%y %H:%M:%S %p')
			d = post['time']
			post['year'] = d.year
			post['month'] = d.month
			post['day'] = d.day

			post['hour'] = d.hour
			post['min'] = d.min

			writer.writerow(post)

except IOError:
	print("I/O error")
