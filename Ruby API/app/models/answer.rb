class Answer < ApplicationRecord
	# model Association 
	belongs_to :question
	belongs_to :user
	# Validations
	validates_presence_of :answer
end
