class Notification < ApplicationRecord
	# model association
	belongs_to :question
	has_many :players_notifications
end
