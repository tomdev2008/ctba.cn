#!/usr/bin/ruby
require 'kconv'
require 'net/smtp'

smtp_server = "smtp.foxmail.com"
smtp_username = "gladstone9"
smth_password = "gladstone"
helo_domain = "smtp.foxmail.com"
from_addr   = "gladstone9@foxmail.com"
to_addr     = "gladstone9@gmail.com;79326901@qq.com;mockee@gmail.com;laoziri@gmail.com;s6xstrings@gmail.com"
subject     = "rebuild of ctba"
date        = Time.now
msg_id      = "msg-"+Time.now.to_s


mail_data = <<EOD
From: #{from_addr}
To: #{to_addr}
Subject: #{subject}
Date: #{date}
Message-Id: #{msg_id}

Hi~ This is the ruby angle host on ctba.cn

We had a rebuild of the main site by ant just right now

EOD

Net::SMTP.start(smtp_server, 25, helo_domain,smtp_username,smth_password,:login) {|smtp|
  smtp.send_mail mail_data, from_addr, to_addr.split(';')
}