class CoachesController < ApplicationController
  before_action :set_coach, only: [:show, :update, :destroy]
  before_action :authenticate_coach!, only: [:destroy]
  
  # GET /coaches
  def index
    @coaches = User.where("is_coach = 1").order('first_name asc')
    json_response(@coaches)
  end

  # POST /coaches
  def create
    @coach = User.create!(coach_params)
    @coach.is_coach = 1
    @coach.save
    json_response(@coach, :created)
  end

  # GET /coaches/:id
  def show
    json_response(@coach)
  end

  # PUT /coaches/:id
  def update
    @coach.update(coach_params)
    head :no_content
  end

  # DELETE /coaches/:id
  def destroy
    @coach.destroy
    head :no_content
  end

  private

  def coach_params
    # whitelist params
    params.permit(:first_name, :last_name, :email, :password, :password_confirmation, :phone)
  end

  def set_coach
    @coach = User.find(params[:id])
  end

end
