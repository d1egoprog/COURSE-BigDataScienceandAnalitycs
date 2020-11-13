from facebook_scraper import get_posts
import csv
import io

csv_columns = ['post_id','text','time','image','images','likes','comments'
				,'shares','reactions', 'post_url',  'post_text', 'user_id'
				, 'video_id', 'video_thumbnail', 'video', 'shared_text', 'link']
csv_file = "results/step02.csv"
try:
	with io.open(csv_file, 'w', encoding="utf-8") as csvfile:
		writer = csv.DictWriter(csvfile, fieldnames=csv_columns, delimiter=';')
		writer.writeheader()
		for post in get_posts('icetexcolombia', pages=5):
			writer.writerow(post)

except IOError:
	print("I/O error")
