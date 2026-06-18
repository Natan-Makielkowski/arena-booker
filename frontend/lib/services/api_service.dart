import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8080/api';
  final _storage = const FlutterSecureStorage();

  Future<void> login(String username, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'username': username,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      await _storage.write(key: 'jwt_token', value: data['token']);
    } else {
      throw Exception('Wrong username or password');
    }
  }


  Future<void> logout() async {
    await _storage.delete(key: 'jwt_token');
  }

  Future<Map<String, String>> _getAuthHeaders() async {
    final token = await _storage.read(key: 'jwt_token');
    return {
      'Content-Type': 'application/json',
      'Authorization': token != null ? 'Bearer $token' : '',
    };
  }


  Future<List<dynamic>> getReservations() async {
    final headers = await _getAuthHeaders();
    final response = await http.get(
      Uri.parse('$baseUrl/reservations'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return json.decode(utf8.decode(response.bodyBytes));
    } else {
      throw Exception('Failed to fetch reservations');
    }
  }

  Future<void> createReservation(String sector, String startTime, String endTime) async {
    final headers = await _getAuthHeaders();
    final response = await http.post(
      Uri.parse('$baseUrl/reservations'),
      headers: headers,
      body: json.encode({
        'sector': sector,
        'startTime': startTime,
        'endTime': endTime,
      }),
    );

    if (response.statusCode == 409) {
      throw Exception('Conflict: This sector is already taken!');
    } else if (response.statusCode != 200 && response.statusCode != 201) {
      throw Exception('An error occurred during reservation');
    }
  }


  Future<void> deleteReservation(int id) async {
    final headers = await _getAuthHeaders();
    final response = await http.delete(
      Uri.parse('$baseUrl/reservations/$id'),
      headers: headers,
    );

    if (response.statusCode != 200 && response.statusCode != 204) {
      throw Exception('Failed to delete reservation');
    }
  }
}