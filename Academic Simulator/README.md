Berikut adalah daftar operasi yang ada pada solusi:

Menambah data dosen (lecturer). Fitur ini telah dikembangkan dan tidak ada perubahan yang perlu dilakukan.
Menambah data mahasiswa (student). Fitur ini telah dikembangkan dan tidak ada perubahan yang perlu dilakukan.
Menampilkan data mahasiswa secara rinci (student detail). Fitur ini telah dikembangkan dan tidak ada perubahan yang perlu dilakukan.
Menambah data mata kuliah (course). Fitur ini telah dikembangkan namun perlu dilakukan perubahan, yakni dengan tidak menyertakan tahun akademik, semester pelaksanaan, serta dosen pengampu.
Menambah data periode pelaksanaan perkuliahan (course opening). Sebuah periode pelaksanaan perkuliahan pada dasarnya merupakan jawaban dari persoalan yang dikemukakan pada awal tugas ini.
Menambah data pengambilan mata kuliah (enrollment). Fitur ini telah dikembangkan sebelumnya namun perlu dilakukan perubahan relasi. Sebelumnya relasi dilakukan dengan mata kuliah (course) kini dengan periode pelaksanaan perkuliahan (course opening).
Pemberian nilai pada pengambilan mata kuliah (grading). Fitur ini telah dikembangkan dan tidak ada perubahan yang perlu dilakukan.
Pemberian nilai remedial pada pengambilan mata kuliah (remedial). Fitur ini telah dikembangkan dan tidak ada perubahan yang perlu dilakukan.
Satu fitur tambahan yang perlu dikembangkan adalah menampilkan sejarah pelaksanaan mata kuliah (course history).
Input:

lecturer-add#0130058501#Parmonangan Rotua Togatorop#PAT#mona.togatorop@del.ac.id#Information Systems
lecturer-add#0114129002#Iustisia Natalia Simbolon#IUS#iustisia.simbolon@del.ac.id#Informatics
lecturer-add#0124108201#Rosni Lumbantoruan#RSL#rosni@del.ac.id#Information Systems
course-add#12S1101#Dasar Sistem Informasi#3#D
student-add#12S20001#Marcelino Manalu#2020#Information Systems
student-add#12S20002#Yoga Sihombing#2020#Information Systems
student-add#12S20003#Marcel Simanjuntak#2020#Information Systems
course-open#12S1101#2020/2021#odd#IUS
enrollment-add#12S1101#12S20001#2020/2021#odd
enrollment-add#12S1101#12S20002#2020/2021#odd
enrollment-add#12S1101#12S20003#2020/2021#odd
enrollment-grade#12S1101#12S20001#2020/2021#odd#B
enrollment-grade#12S1101#12S20002#2020/2021#odd#B
enrollment-grade#12S1101#12S20003#2020/2021#odd#B
course-open#12S1101#2020/2021#even#IUS
course-open#12S1101#2021/2022#odd#IUS
enrollment-add#12S1101#12S20002#2021/2022#odd
enrollment-add#12S1101#12S20003#2021/2022#odd
enrollment-grade#12S1101#12S20002#2021/2022#odd#AB
enrollment-grade#12S1101#12S20003#2021/2022#odd#AB
enrollment-add#12S1101#12S20001#2020/2021#even
enrollment-add#12S1101#12S20002#2020/2021#even
enrollment-add#12S1101#12S20003#2020/2021#even
enrollment-grade#12S1101#12S20001#2020/2021#even#AB
enrollment-grade#12S1101#12S20002#2020/2021#even#A
enrollment-grade#12S1101#12S20003#2020/2021#even#A
course-history#12S1101
---

Output:

12S1101|Dasar Sistem Informasi|3|D|2020/2021|odd|IUS (iustisia.simbolon@del.ac.id)
12S1101|12S20001|2020/2021|odd|B
12S1101|12S20002|2020/2021|odd|B
12S1101|12S20003|2020/2021|odd|B
12S1101|Dasar Sistem Informasi|3|D|2021/2022|odd|IUS (iustisia.simbolon@del.ac.id)
12S1101|12S20002|2021/2022|odd|AB
12S1101|12S20003|2021/2022|odd|AB
12S1101|Dasar Sistem Informasi|3|D|2020/2021|even|IUS (iustisia.simbolon@del.ac.id)
12S1101|12S20001|2020/2021|even|AB
12S1101|12S20002|2020/2021|even|A
12S1101|12S20003|2020/2021|even|A
0130058501|Parmonangan Rotua Togatorop|PAT|mona.togatorop@del.ac.id|Information Systems
0114129002|Iustisia Natalia Simbolon|IUS|iustisia.simbolon@del.ac.id|Informatics
0124108201|Rosni Lumbantoruan|RSL|rosni@del.ac.id|Information Systems
12S1101|Dasar Sistem Informasi|3|D
12S20001|Marcelino Manalu|2020|Information Systems
12S20002|Yoga Sihombing|2020|Information Systems
12S20003|Marcel Simanjuntak|2020|Information Systems
12S1101|12S20001|2020/2021|odd|B
12S1101|12S20002|2020/2021|odd|B
12S1101|12S20003|2020/2021|odd|B
12S1101|12S20002|2021/2022|odd|AB
12S1101|12S20003|2021/2022|odd|AB
12S1101|12S20001|2020/2021|even|AB
12S1101|12S20002|2020/2021|even|A
12S1101|12S20003|2020/2021|even|A
