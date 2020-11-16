//
//  Character.swift
//  Marvel
//
//  Created by Diego on 16/11/2020.
//

import Foundation

struct Character: Codable {
    let id: Int
    let name, description, modified: String
    let resourceURI: String
    let thumbnail: Thumbnail
    
    func thumbnailUrl(imageVariant: String) -> URL {
        let url =  "\(thumbnail.path)/\(imageVariant).\(thumbnail.thumbnailExtension)"
        var urlComponents = URLComponents(url: URL(string: url)!, resolvingAgainstBaseURL: false)
        urlComponents?.scheme = "https"
        return urlComponents!.url!
    }
}

struct Thumbnail: Codable {
    let path, thumbnailExtension: String
    enum CodingKeys: String, CodingKey {
        case path
        case thumbnailExtension = "extension"
    }
}
