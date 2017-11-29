class MultipleAnswersController < ApplicationController
  include NotificationTracker

  def create_multiple
		# puts "This is "
		# puts params
		# puts "ohhhh yaa"
    total_points = 0
    user_id = nil
		@answers = params["_json"].map do |params_hash|
			whitelisted_params = params_hash.permit( :answer, :user_id, :question_id, :points)
			Answer.new(whitelisted_params)
		end

    # Check that all the answers are valid and can be saved
    if @answers.all? { |answer| answer.valid? }
      # Now we know they are valid, we save each answer
      @answers.each do |answer|
        answer.save
        user_id = answer[:user_id] unless user_id != nil
        puts "The answer was worth this many points below:"
        puts answer[:points]
        question_order = Question.find(answer[:question_id])[:question_order]
        answer.update(question_order: question_order)
        total_points += answer[:points].to_i
        question_pattern = Question.find(answer[:question_id])[:pattern_multi]
        if answer[:points] == 1 && question_pattern == 1
          incrementNotification(answer[:question_id], answer[:user_id])
        elsif answer[:points] != 1 && question_pattern == 1
          resetCounter(answer[:question_id], answer[:user_id])
        end
      end

      # and then respond with the json versions of the saved ticker_activites
      puts "You got this many points below:"
      puts total_points
      addPointsToPlayer(user_id, total_points)
      json_response(@answers, :created)
    else
      # Since at least some of the answers are invalid,
      # we can't save *all* the answers, so we
      # respond with the validation errors instead
      @errors = @answers.map { |answer|
        answer.errors
      }
      json_response(@errors, :unprocessable_entity)
    end
	end
end
