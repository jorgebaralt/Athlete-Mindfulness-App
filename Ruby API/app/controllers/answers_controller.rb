class AnswersController < ApplicationController
	before_action :set_answer, only: [:show, :update, :destroy]
  include NotificationTracker

  # add params[:day]

  # GET /answers
  def index
    if params[:day] != nil
      # used to get answers for a particular day
      begday = params[:day].to_date.beginning_of_day
      endday = params[:day].to_date.end_of_day
      if params[:user_id] != nil and params[:question_id] != nil and params[:points] != nil
        @answers = Answer.where("user_id = ? AND question_id = ? AND points = ? AND created_at >= ? AND created_at < ?", params[:user_id], params[:question_id], params[:points], begday, endday).order('question_order asc')
      elsif params[:user_id] != nil and params[:question_id] != nil
        @answers = Answer.where("user_id = ? AND question_id = ? AND created_at >= ? AND created_at < ?", params[:user_id], params[:question_id], begday, endday).order('question_order asc')
      elsif params[:points] != nil and params[:question_id] != nil
        @answers = Answer.where("points = ? AND question_id = ? AND created_at >= ? AND created_at < ?", params[:points], params[:question_id], begday, endday).order('question_order asc')
      elsif params[:user_id] != nil and params[:points] != nil
        @answers = Answer.where("user_id = ? AND points = ? AND created_at >= ? AND created_at < ?", params[:user_id], params[:points], begday, endday).order('question_order asc')
      elsif params[:points] != nil
        @answers = Answer.where("points = ? AND created_at >= ? AND created_at < ?", params[:points], begday, endday).order('question_order asc')
      elsif params[:user_id] != nil
        @answers = Answer.where("user_id = ? AND created_at >= ? AND created_at < ?", params[:user_id], begday, endday).order('question_order asc')
      elsif params[:question_id] != nil
        @answers = Answer.where("question_id = ? AND created_at >= ? AND created_at < ?", params[:question_id], begday, endday).order('question_order asc')
      else
        @answers = Answer.where("created_at >= ? AND created_at < ?", begday, endday).order('question_order asc')
      end
    else 
      if params[:user_id] != nil and params[:question_id] != nil and params[:points] != nil
        @answers = Answer.where("user_id = ? AND question_id = ? AND points = ?", params[:user_id], params[:question_id], params[:points]).order('question_order asc')
      elsif params[:user_id] != nil and params[:question_id] != nil
        @answers = Answer.where("user_id = ? AND question_id = ?", params[:user_id], params[:question_id]).order('question_order asc')
      elsif params[:points] != nil and params[:question_id] != nil
        @answers = Answer.where("points = ? AND question_id = ?", params[:points], params[:question_id]).order('question_order asc')
      elsif params[:user_id] != nil and params[:points] != nil
        @answers = Answer.where("user_id = ? AND points = ?", params[:user_id], params[:points]).order('question_order asc')
      elsif params[:points] != nil
        @answers = Answer.where("points = ?", params[:points]).order('question_order asc')
      elsif params[:user_id] != nil
        @answers = Answer.where("user_id = ?", params[:user_id]).order('question_order asc')
      elsif params[:question_id] != nil
        @answers = Answer.where("question_id = ?", params[:question_id]).order('question_order asc')
      else
        @answers = Answer.all.order('question_order asc')
      end
    end
    json_response(@answers)
  end

  # POST /answers
  def create
    puts params 
    @answer = Answer.create!(answer_params)
    question_order = Question.find(params[:question_id])[:question_order]
    @answer.update(question_order: question_order)
    addPointsToPlayer(params[:user_id], params[:points]);
    question_pattern = Question.find(params[:question_id])[:pattern_multi]
    if @answer[:points] == 1 && question_pattern == 1
      incrementNotification(params[:question_id], params[:user_id])
    elsif @answer[:points] != 1 && question_pattern == 1
      puts params
      resetCounter(params[:question_id], params[:user_id])
    end
    json_response(@answer, :created)
  end

  # GET /answers/:id
  def show
    json_response(@answer)
  end

  # PUT /answers/:id
  def update
    @answer.update(answer_params)
    head :no_content
  end

  # DELETE /answers/:id
  def destroy
    @answer.destroy
    head :no_content
  end

  private

  def answer_params
    # whitelist params
    params.permit( :answer, :user_id, :question_id, :points)
  end

  def set_answer
    @answer = Answer.find(params[:id])
  end
end