//
//  Response.swift
//  Marvel
//
//  Created by Diego on 15/11/2020.
//

import Foundation

struct MarvelResponse: Codable {
    let code: Int
    let status, copyright, attributionText: String
    let data: DataResponse
    let etag: String
}

struct DataResponse: Codable {
    let offset, limit, total, count: Int
    let results: [Character]
}
