class LeaderboardController < ApplicationController

  # GET /users
  def index
  	if params[:coach_id] != nil and params[:age_range] != nil and params[:gender] != nil
      @users = User.where("coach_id = ? AND age_range = ? AND gender = ? AND is_coach = 0", params[:coach_id], params[:age_range], params[:gender]).order('points desc')
    elsif params[:coach_id] != nil and params[:gender] != nil
      @users = User.where("coach_id = ? AND gender = ? AND is_coach = 0", params[:coach_id], params[:gender]).order('points desc')
    elsif params[:gender] != nil and params[:age_range] != nil
      @users = User.where("gender = ? AND age_range = ? AND is_coach = 0", params[:gender], params[:age_range]).order('points desc')
    elsif params[:coach_id] != nil and params[:age_range] != nil
      @users = User.where("coach_id = ? AND age_range = ? AND is_coach = 0", params[:coach_id], params[:age_range]).order('points desc')
    elsif params[:coach_id] != nil
      @users = User.where("coach_id = ? AND is_coach = 0", params[:coach_id]).order('points desc')
    elsif params[:age_range] != nil
      @users = User.where("age_range = ? AND is_coach = 0", params[:age_range]).order('points desc')
    elsif params[:gender] != nil
      @users = User.where("gender = ? AND is_coach = 0", params[:gender]).order('points desc')
    else
    	@users = User.where("is_coach = 0").order('points desc')
    end
    json_response(@users)
  end

  def user_params
    # whitelist params
    params.permit( :first_name, :last_name, :email, :password, :password_confirmation, :phone, :gender, :points, :coach_id, :age, :age_range, :is_coach)
  end
end
